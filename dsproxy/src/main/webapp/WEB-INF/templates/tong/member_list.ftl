<#include "/include/header.ftl">
<card title="${game_title}"><p>
帮友列表<br/>
<#if (page_objs?size>0)>
	<#list page_objs as tongPlayer>		
		<@a href="/p?p_VO/${pid}/${tongPlayer.playerId}"/>${tongPlayer.playerName?if_exists}</a>
		<#if tongPlayer.location?exists>${tongPlayer.location}<#else>离线</#if>  贡献 ${tongPlayer.mark}
		<br/>
	</#list>
	<#if (page>0)><@a href="/p?t_ML/${pid}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?t_ML/${pid}/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
</#if>
<br/>
<@a href="/p?t_M/${pid}/"/>返回帮会</a><br/>
<@gogame/>
</p>
</card>
</wml>
