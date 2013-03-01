<#include "/include/header.ftl">
<#include "/include/player.ftl">
<card title="${game_title}">
<p>
<#if (page_objs?size>0)>
	<#if viewFriendPresent?exists && receiver?exists>${receiver.name}<#else>你</#if>最近收到的礼物：<br/>
	<#list page_objs as itemMap>		
		<@a href="/p?p_VF/${pid}/${itemMap.presentPlayer.getId()}"/>${itemMap.presentPlayer.getName()}</a>送的<img alt="" src="/images/presents/16/${itemMap.item.getId()}.gif"/><#if viewFriendPresent?exists && receiver?exists>${itemMap.item.getName()}<#else><@a href="/p?ps_VS/${pid}/${itemMap.presentPlayer.getId()}/${itemMap.item.getId()}/0"/>${itemMap.item.getName()}</a></#if>(${itemMap.presentTime}) 
		<#if viewFriendPresent?exists && receiver?exists>
		
		<#else>
		<@a href="/p?ps_M/${pid}/${itemMap.presentPlayer.getId()}"/>回礼</a>
		</#if>
		<br/>
	</#list>
	礼物总价值：${worth}<br/>
	<#if viewFriendPresent?exists && receiver?exists>
		<#if (page>1)><@a href="/p?ps_MP/${pid}/${receiver.id}/1"/>首页.</a><@a href="/p?ps_MP/${pid}/${receiver.id}/${page-1}"/>上页.</a></#if>
		<#if (page<total_page)><@a href="/p?ps_MP/${pid}/${receiver.id}/${page+1}"/>下页.</a></#if>
		<#if (total_page>1)>(${page}/${total_page})<br/></#if>
	<#else>
		<#if (page>1)><@a href="/p?ps_MP/${pid}/1"/>首页.</a><@a href="/p?ps_MP/${pid}/${page-1}"/>上页.</a></#if>
		<#if (page<total_page)><@a href="/p?ps_MP/${pid}/${page+1}"/>下页.</a></#if>
		<#if (total_page>1)>(${page}/${total_page})<br/></#if>
	</#if>
<#else>
	<#if viewFriendPresent?exists && receiver?exists>${receiver.name}<#else>你</#if>还没有收到礼物<br/>
	<#if viewFriendPresent?exists && receiver?exists>
	<#else>
	<@a href="/p?p_LF/${pid}/0"/>给好友送礼吧</a><br/>
	</#if>
</#if>
<#if viewFriendPresent?exists && receiver?exists><@a href="/p?ps_M/${pid}/${receiver.id}"/>给${receiver.name}送礼物</a><br/></#if>
<@goback />
<@gogame/>
</p>
</card>
</wml>