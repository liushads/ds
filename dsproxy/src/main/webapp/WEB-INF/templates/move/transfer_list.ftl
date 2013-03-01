<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
<#if objs?exists && (objs?size>0)>
	请选择你要传送的目的地，货物无法被传送，将被丢弃。<br/>
	<#list objs as obj> 		
		<@a href="/p?mv_TrC/${pid}/${obj[0].id}"/>${obj[0].name}(${obj[2].name}里)</a>
		<#if (obj[1].id?exists && obj[1].id != "")>(<@a href="/p?t_V/${pid}/${obj[1].id}"/>${obj[1].name}</a>)</#if><br/>
	</#list>
<#else>
	你还没有该地区的地图，你必须先获得该地区的地图。<br/>
</#if>
点击选择其它地区:<br/>
<#list areas as aa>
	<#if area = aa.id>
		${aa.name}·
	<#else>
		<@a href="/p?mv_TrL/${pid}/${aa.id}"/>${aa.name}</a>·
	</#if>
</#list>
<br/>
<@gofacility/>
</p></card>
</wml>
