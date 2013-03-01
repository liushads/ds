<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>

可赠送物品列表：<br/>
<#if itemType=='1'>
【装备|<@a href="/p?i_SG/${pid}/${otherId}/2/"/>宝石</a>|<@a href="/p?i_SG/${pid}/${otherId}/3/"/>药品</a>】<br/>
</#if>
<#if itemType=='2'>
【<@a href="/p?i_SG/${pid}/${otherId}/1/"/>装备</a>|宝石 |<@a href="/p?i_SG/${pid}/${otherId}/3/"/>药品</a>】<br/>
</#if>
<#if itemType=='3'>
【<@a href="/p?i_SG/${pid}/${otherId}/1/"/>装备</a>|<@a href="/p?i_SG/${pid}/${otherId}/2/"/>宝石</a>|药品】<br/>
</#if>

<#if (page_objs?size>0)>
<#if itemType=='3' || itemType=='2'|| itemType=='13'>
<#list page_objs as playerItem>
<@a href="/p?i_SGI/${pid}/${otherId}/${playerItem.id}/${playerItem.item.id}"/>${playerItem.item.name}X${playerItem.amount}</a><br/>
</#list>
<#else>
<#list page_objs as playerItem>
<@a href="/p?i_SGI/${pid}/${otherId}/${playerItem.id}/${playerItem.item.id}"/><@showName playerItem/></a><br/>
</#list>
</#if>
	<#if (page>0)><@a href="/p?i_SG/${pid}/${otherId}/${itemType}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?i_SG/${pid}/${otherId}/${itemType}/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
<#else>
列表为空.<br/>
</#if>
<br/>
<@goback/>
<@gogame/>

</p>
</card>
</wml>