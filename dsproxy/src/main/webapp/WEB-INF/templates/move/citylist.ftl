<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
<#if cities?exists && (cities?size>0)>
	请选择您要去往的城镇。<br/>
	<#list cities as e> 
		<#if (e.id != city) && (e.level != 0) >
			<@a href="/p?mv_TvS/${pid}/${e.id}"/>${e.name}</a>
			<#if e.tongId?exists && e.tongId != 0>
			(<@a href="/p?t_V/${pid}/${e.tongId}"/>${e.tongName}</a>)
			</#if>
			<br/>
	 	</#if>
	</#list>
<#else>
	你还没有该地区的地图，你必须先获得该地区的地图。<br/>
</#if>
点击选择其它地区:<br/>
<#list areas as e>
	<#if area = e.id>
		${e.name}·
	<#else>
		<@a href="/p?mv_LC/${pid}/${e.id}/"/>${e.name}</a>·
	</#if>
</#list>
<br/>
<@gofacility/>
</p></card>
</wml>
