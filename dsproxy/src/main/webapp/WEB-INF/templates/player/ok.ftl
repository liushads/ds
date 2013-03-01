<#include "/include/header.ftl">
<card title="${game_title}"><p>
	<#if dialog?exists && dialog.dialog?exists && dialog.dialog != "" >
		<#if dialog.icon?exists && dialog.icon != "">
			<img alt="" src="${img_server}/${dialog.icon}" /><br/>
		</#if>
		${dialog.dialog}<br/>
	</#if>
	<#if dialog?exists && dialog.actions?exists && dialog.actions?size &gt; 0>
		<#list dialog.actions as ls>
			<#assign param_tmp = ""/>
			<#assign custom_tmp = ""/>
			<#if ls.param?exists><#assign param_tmp = ls.param + "/"/></#if>
			<#if custom?exists><#assign custom_tmp = custom/></#if>
		
			<a href="<@a href="p?${ls.command}/${pid}/${param_tmp}${custom_tmp}"/>${ls.name}</a>
		</#list>
		<br/>
	</#if>
<@gofacility/>
</p></card>
</wml>