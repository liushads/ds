<#include "/include/header.ftl">
<#include "/include/player.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
<#if other?exists>
摆摊的物品列表：<@a href="/p?ex_SP/${pid}/${other.id}/0"/>物品</a><br/>
<#if player_pet?exists>
<#if player_pet?size == 0>
没有宠物出售<br/>
<#else>
<#list player_pet as pet>
		<@a href="/p?ex_SPT/${pid}/${other.id}/${pet.id}"/>${pet.customName}</a>x1(${pet.money/100}金票)<br/>
</#list>
</#if>
</#if>
<#else>
用户离线或者暂无摆摊物品<br/>
</#if>
<@gogame/>
</p></card>
</wml>
