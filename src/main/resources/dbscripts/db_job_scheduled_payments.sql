create or replace package body IB_PKG is

procedure sendPaymentScheduled is
  
  v_cur sys_refcursor;
  v_row ib_payment_scheduled%rowtype;
  
  v_to_account_id number; 
  v_from_account_balance number;
  v_payment_sched_amount number;
  
  begin
    
    --nacti vsechny pravidelne platby, 
    --ktere maji tento den odejit
    open v_cur for 
     select * from ib_payment_scheduled t
     where 1=1
           and trunc(t.end_date) > trunc(sysdate)  
           and 
           --mesicne
           (((extract(day from t.payment_date) = 
                 extract(day from sysdate))
                 and 
                 (t.periode = 'MONTHLY')) 
           --rocne
           or ((extract(day from t.payment_date) = 
                 extract(day from sysdate))
                 and 
               (extract(month from t.payment_date) = 
                 extract(month from sysdate))
                 and
                 (t.periode = 'YEARLY'))); 
     
     loop
       fetch v_cur into v_row;
       exit when v_cur%notfound;
       
       --nacti ID ciloveho uctu a castku pravidelne platby
       select t.id,
              pi.amount
       into v_to_account_id,
            v_payment_sched_amount
       from ib_account t 
       join ib_payment_info pi
          on pi.id = v_row.payment_info_id
       where t.account_number = pi.account_number;
       
       --zjisti jestli na odchozim uctu je dostatek prostredku
       select t.balance
       into v_from_account_balance
       from ib_account t
       where t.id = v_row.account_id; 
       
       if v_from_account_balance >= v_payment_sched_amount  then  
       
        --sniz castku na odchozim uctu
        update ib_account t 
        set t.balance = (t.balance - v_payment_sched_amount)
        where t.id = v_row.account_id;
        
        --zvys castku na cilovem uctu
        update ib_account t 
        set t.balance = (t.balance + v_payment_sched_amount)
        where t.id = v_to_account_id;
        
        --vloz platbu
        insert into ib_payment 
        (from_account_id,to_account_id,
         payment_info_id,payment_date)
        values
        (v_row.account_id,v_to_account_id,
         v_row.payment_info_id,sysdate);
         
       end if;
       
     end loop;
  
  commit;
  
exception
  when others then
    rollback;

end sendPaymentScheduled;


end IB_PKG;
