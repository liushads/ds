<#include "/include/pet.ftl">
<#include "/include/sect.ftl">
<#macro showMoney money>
<#assign silver = (money/1000)?int>
<#assign copper = money%1000>
<#if silver!=0>${silver}银</#if>${copper}铜
</#macro>
<#macro showAllMoney player,isCopper>
<@showMoney player,isCopper/>
</#macro>
<#macro showGold player>
金锭:${player.gold/100} 金票:${player.advGold/100}<br/>
</#macro>
<#-- 显示玩家状态 -->
<#macro showPlayer player>
<#local txName =player.sectId + "_"+ player.sex/>
<img alt="" src="${img_server}/${txName}.gif"/><br/>
昵称:${player.name}<br/>
ID&nbsp;&nbsp;：${player.id}<br/>
性别：<#if player.sex == 0>女<#else>男</#if><br/>
等级：${player.level}<br/>
经验：${player.exp}/${player.dyn.maxExp}<br/>
体力：${player.hp}/${player.dyn.maxHp}<br/>
攻击：${player.dyn.attackMin}-${player.dyn.attackMax}<br/>
防御：${player.dyn.defence}<br/>
门派：${label_sect_name}<br/>
疲劳值：${player.fame}/${player.dyn.fame}<br/>
暴击：${player.dyn.crit/100}%<br/>
格挡：${player.dyn.parry/100}%<br/>
体质：${player.dyn.constitution}<br/>
力量：${player.dyn.forces}<br/>
敏捷：${player.dyn.agility}<br/>
智力：${player.dyn.intelligence}<br/>
签名：${player.description}<br/>
</#macro>

<#macro showMoney player,isCopper>
	<#if (isCopper > 0)>
		金币：${(player.copper)}<br/>
	</#if>
	钻石：${player.advGold/100}<br/>
	水晶：${player.gold/100}<br/>
</#macro>

<#macro showPlayerDegree player>
<#--
<#if player.bookVip?exists && player.bookVip == 1>
<img alt="" src="${img_server}/bookstore_vip.gif"/>
</#if>
-->
<#if player.playerMember?exists>
<img alt="" src="${img_server}/member_vip_${player.playerMember.memberLevel}.png"/><@a href="/p?p_m/${pid}/m/"/>会员用户</a>
<#else>
<@a href="/p?p_m/${pid}/p/"/>普通用户</a>
</#if>
</#macro>
