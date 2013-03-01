<#-- 重命名 -->
<#include "/include/header.ftl">
<#include "/include/pet.ftl">
<card title="${game_title}"><p>
<#if desc?exists>
${desc}<br/>
</#if>
<#if player_pet?exists>
	给<@a href="/p?pe_ReP/${pid}/${player_pet.id}/"/>${player_pet.customName}</a>命名成功.<br/>
	
	<@showPet player,player_pet/><br/>
	<@goPet/>
</#if>
<@gogame/>
</p></card>
</wml>