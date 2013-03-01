<#-- 宠物孵化 -->
<#include "/include/header.ftl">
<#include "/include/pet.ftl">
<card title="${game_title}"><p>
<#if desc?exists>
${desc}<br/>
</#if>
<#if player_pet?exists>
	恭喜你获得了宠物${player_pet.pet.name},属性：<br/> 
	等级:${player_pet.level}<br/>
	攻击:${player_pet.attack}<br/>
	防御:${player_pet.defence}<br/>
	敏捷:${player_pet.agility}<br/>
	给它起个响亮的名字吧：<br/>
	<input type="text" name="custom_name" value="${player_pet.pet.name}" maxlength="15"></input><br/>
	<anchor>确定
	<@go href="/p?pe_Re/${pid}/" method="post"/>
		<postfield name="1" value="${player_pet.id}" />
		<postfield name="2" value="$custom_name" />
	</go>
	</anchor>
</#if>
<br/>
<@gogame/>
</p></card>
</wml>