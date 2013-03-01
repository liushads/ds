<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if td?exists>
${td}
</#if>
</p>
<@goback/>
<@gofacility/>
</card>
</wml>