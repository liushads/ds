<#include "/include/header.ftl">
<#include "/include/player.ftl">
<card title="${game_title}"><p>
会员用户<br/>
<#if info?exists>
${info}<br/>
</#if>
<#if MR?exists>
会员福利介绍:<br/>
<@showMemberLevelRewards player/>
</#if>
<@goback/>
<@gogame/>
</p></card>
</wml>