<#include "/include/header.ftl">
<#include "/include/head_card.ftl">
<#include "award.ftl">
<#if label_award_prize?exists>
获得奖励<br/>
${label_award_prize}<br/>
</#if>
<@goback/>
<@gogame/>
<#include "/include/foot_card.ftl">