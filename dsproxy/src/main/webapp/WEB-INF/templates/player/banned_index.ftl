<#include "/include/header.ftl">
<card title="${game_title}"><p>
禁言名单：<br/>
<#list page_objs as gmlog>
玩家：${gmlog.player.name}        解禁时间：${gmlog.time}  被禁理由:${gmlog.msg}  <@a href="/p?p_VO/${pid}/${gmlog.playerId}/"/>解禁</a><br/><br/>
</#list>
<#if (page>0)><@a href="p?p_B/${pid}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="p?p_B/${pid}/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
<@goback/>
<@gogame/>
</p>
</card>
</wml>