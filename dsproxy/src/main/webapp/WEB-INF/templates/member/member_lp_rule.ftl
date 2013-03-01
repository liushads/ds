<#include "/include/header.ftl">
<#include "/include/player.ftl">
<card title="${game_title}"><p>
会员福利介绍:<br/>
<#if playerMember?exists>
我的会员等级:${playerMember.memberLevelStr}级<br/>
</#if>
<@showMemberLevelRewards player/>

<@goback/>
<@gogame/>
</p></card>
</wml>