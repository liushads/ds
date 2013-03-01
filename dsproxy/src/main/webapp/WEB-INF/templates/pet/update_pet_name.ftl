<#-- 宠物改名 -->
<#include "/include/header.ftl">
<#include "/include/pet.ftl">
<card title="${game_title}"><p>
<#if player_pet?exists><#-- 改名的宠物 -->
每次宠物改名需要100银币<br/>
	你宠物名称${player_pet.customName}?<br/>
	<input name="name" maxlength="12" emptyok="false" type="text" value=""/><br/>
	<anchor>确定
	<@go href="/p?pe_UP/${pid}/" method="post"/>
			<postfield name="1" value="${player_pet.id}" />
			<postfield name="2" value="$name" />
	</go>
</anchor>	
<br/>
<#else>
修改成功<br/>
</#if>

<@gogame/>
</p></card>
</wml>