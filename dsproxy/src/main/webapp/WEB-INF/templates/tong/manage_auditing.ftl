<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if desc?exists>
${desc}<br/>
</#if>
入帮管理<br/>
申请列表：（按等级排序）<br/>
<#if (page_objs?size>0)>
	<#list page_objs as tongPlayer>		
		<@a href="/p?t_MVP/${pid}/${tongPlayer.playerId}/ap"/>${tongPlayer.playerName}</a>
		<#-- <#if tongPlayer.location?exists>${tongPlayer.location}<#else>离线</#if> --> (${tongPlayer.playerLevel}级)
		<br/>
	</#list>
	<#if (page>0)><@a href="/p?t_MAu/${pid}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?t_MAu/${pid}/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
</#if>

<@a href="/p?t_MAuA/${pid}/allowall"/>全部批准</a> <@a href="/p?t_MAuA/${pid}/refuseall"/>全部拒绝</a><br/>

<@a href="/p?t_M/${pid}/"/>返回帮会</a><br/>
<@gogame/>
</p>
</card>
</wml>

