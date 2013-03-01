<#include "/include/header.ftl">
<#include "/include/player.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>

我的摊位：<@a href="/p?p_VSA/${pid}/0"/>物品</a> <@a href="/p?ex_VSA/${pid}/1"/>一键收摊</a>
<#if player_pet?exists && (player_pet?size>0)>
<br/>
	<#list player_pet as pet>
		<@a href="/p?ex_SPC/${pid}/${pet.id}"/>${pet.customName}</a>x1(${pet.money/100}金票)<br/>
	</#list>
<#else>
<br/>
暂无摆摊的宠物<br/>	
</#if>
<br/>
<@gogame/>
</p>
</card>
</wml>