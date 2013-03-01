<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if td?exists>
${td}<br/>
</#if>
<@goback/>
<@gofacility/>
<@gogame/>
</p></card>
</wml>