<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if err_msg?exists>
${err_msg.text}
</#if>
<br/>
<@gofacility/>
</p></card>
</wml>