<#-- 天赋遗忘确认界面 -->
<#include "/include/header.ftl">
<#include "/include/pet.ftl">
<card title="${game_title}"><p>
<#if item?exists>
你需要${item.name}才能把宠物技能进行遗忘.<br/>
</#if>
<#if (player_pet?exists && pet_talent?exists)>	
	你确认要把技能【${pet_talent.name}】遗忘掉吗.<br/>
	<@a href="/p?pe_FT/${pid}/${player_pet.id}/${pet_talent.id}/"/>确定</a><br/>
	<@a href="/p?pe_MT/${pid}/${player_pet.id}/"/>返回</a><br/>
</#if>
<@gogame/>
</p></card>
</wml>