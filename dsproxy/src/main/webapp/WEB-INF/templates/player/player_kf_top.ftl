<#include "/include/header.ftl">
<card title="${game_title}"><p>
斗神荣誉度排行榜：<br/>
<#if page_objs?exists && page_objs?size &gt; 0>
<#list page_objs as player>
		<@a href="/p?p_VO/${pid}/${player.id}"/>${player.name}(${player.level}级)(${player.kf.honorValue}荣誉度)
		</a><br/>
	</#list>
	<#if (page>0)><@a href="/p?p_KF/${pid}/l/${page-1}/"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="/p?p_KF/${pid}/l/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
	<#else>
	榜上无人，期待您的参与<br/>
	</#if>
<@goback />
<@gogame />
</p></card>
</wml>