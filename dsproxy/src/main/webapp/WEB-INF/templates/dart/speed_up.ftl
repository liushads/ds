<#include "/include/header.ftl">
<#include "/include/head_card.ftl">
<#if label_go_gold?exists>
您确认花费${label_go_gold/100}钻石减少${label_go_time}分钟运镖时间<br/>
<@a href="/p?d_SU/${pid}/0/ok/"/>确定</a><br/>
</#if>
<#if label_go_total_gold?exists>
您确认花费${label_go_total_gold/100}钻石完成全部运镖时间<br/>
<@a href="/p?d_SU/${pid}/1/ok/"/>确定</a><br/>
</#if>
<@goback/>
<@gogame/>
<#include "/include/foot_card.ftl">