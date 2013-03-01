<#include "/include/header.ftl">
<card title="${game_title}">
<p>
擂台：你已经进入高级擂台；请选择设置擂台或者挑战擂主；设置擂台后请注意刷新页面等待你的挑战者，选择擂主后直接进入擂台赛；<br/>
<@a href="/p?f_CL/${pid}/gao/she"/>设置擂台(100银币)</a><br/>
选择攻擂：<br/>
<#if gaolist?exists && (gaolist?size>0)>
<#list gaolist as player>
 <#if player.cityFacilityId==seller.cityFacilityId&&player.id!=seller.id>
  ${player.name}(等级:${player.level}级)   <@a href="/p?f_CL/${pid}/gong/${player.id}/gaoji"/>攻擂</a><br/>
</#if>
</#list>
<#else>
暂时没有人设置擂台<br/>
</#if>
<@goback/>
<@gofacility/>
</p>
</card>
</wml>
 