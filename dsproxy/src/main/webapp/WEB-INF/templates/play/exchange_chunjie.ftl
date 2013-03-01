<#include "/include/header.ftl">
<card title="${game_title}"><p>
兑换奖励:
<#if td?exists>
${td}<br/>
</#if>
<#if miao?exists>
${miao}<br/>
</#if>
<@a href="/p?ply_JL/${pid}/${num}"/>确认兑换</a><br/>
<@goback/>
<@gogame/>
</p>
</card>
</wml>