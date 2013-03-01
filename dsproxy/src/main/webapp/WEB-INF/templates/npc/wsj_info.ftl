<#include "/include/header.ftl">
<card title="${game_title}"><p>
小雪：<br/>
<#if info?exists>
${info}<br/>
</#if>
<#if commitMission?exists>
任务状态:<#if monster901516?exists>幽灵(${monster901516}/10),</#if>
<#if monster901517?exists>吸血鬼(${monster901517}/10),</#if>
<#if monster901518?exists>狼人(${monster901518}/10),</#if>
<#if monster901519?exists>石像鬼(${monster901519}/10),</#if>
<#if monster901600?exists>木乃伊(${monster901600}/10),</#if>
<#if monster901601?exists>骷髅卫兵(${monster901601}/10)</#if>
<br/>
</#if>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>