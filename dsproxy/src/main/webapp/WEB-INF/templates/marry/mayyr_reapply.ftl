<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if err_msg?exists>
${err_msg.text}<br/>
<@a href="/p?pm_PD/${pid}/3/"/>花费500银强制离婚</a><br/>
<@a href="/p?pm_PD/${pid}/7/"/>花费5金强制离婚</a><br/>
<#else>
状态错误<br/>
</#if>
<@goback />
<@gogame />
</p>
</card>
</wml>