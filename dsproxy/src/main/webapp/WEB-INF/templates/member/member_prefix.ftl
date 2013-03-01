<#include "/include/header.ftl">
<#include "/include/player.ftl">
<card title="${game_title}"><p>
<#if action?exists && action=='p'>
您现在不是会员用户加入后您可以享受如下特权:<br/>
</#if>
<@showMemberIntro player/>
<#if action?exists && action=='p'>
<@a href="/p?p_m/${pid}/b/"/>购买会员</a><br/>
<@a href="/p?p_m/${pid}/mr/"/>查看会员福利</a><br/>
</#if>
<@goback/>
</p></card>
</wml>