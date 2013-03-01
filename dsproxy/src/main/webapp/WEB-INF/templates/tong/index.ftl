<#include "/include/header.ftl">
<card title="${game_title}"><p>
	
	<@a href="/p?t_Cr/${pid}/"/>创建</a><br/>
	<#list tongs as t>
	<@a href="/p?t_V/${pid}/${t.id}"/>${t.name}</a><br/>
	</#list>
	
	<#if tong_player?exists>
		<@a href="/p?t_M/${pid}/"/>我的帮会</a><br/>
	</#if>
	
	<@a href="/p?n_L/${pid}/"/>公告</a><br/>
	
	<@goback/>
	<@gogame/>
	</p>
</card>
</wml>