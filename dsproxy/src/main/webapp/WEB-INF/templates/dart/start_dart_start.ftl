<#include "/include/header.ftl">
<#include "/include/head_card.ftl">
<#if label_time?exists && label_day_time?exists>
今日已进行运镖${label_day_time}次当前运镖剩余时间：${(label_time/60)?int}分${(label_time)%60}秒<br/>
<@speedUp /><br/>
</#if>
<@goback/>
<@gogame/>
<#include "/include/foot_card.ftl">