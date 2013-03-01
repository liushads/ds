<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if err_msg?exists>
${err_msg.text}<br/>
<#if err_msg.obj?exists>
<@showPlayerItem err_msg.obj/>
</#if>
</#if>
<@gogame/>
</p>
</card>
</wml>