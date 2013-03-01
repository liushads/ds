<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if td?exists>
${td}<br/>
</#if>
<@gogame/>
</p>
</card>
</wml>