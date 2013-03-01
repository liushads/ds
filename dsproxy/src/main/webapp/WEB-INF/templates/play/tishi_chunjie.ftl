<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if info?exists>
${info}<br/>
</#if>
<#if commitMission?exists>
任务状态:
<#if monster910004?exists>小年兽乐乐(${monster910004}/10),</#if>
<#if monster910003?exists>小年兽喜喜(${monster910003}/10),</#if>
<#if monster910002?exists>小年兽欢欢(${monster910002}/10)</#if>
<br/>
</#if>
<@goback/>
<@gogame/>
</p></card>
</wml>
