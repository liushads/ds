<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
与【${friend.name}】的婚宴中，等待他人的祝贺吧。<br/>
<@refresh/>
已有(${marry_cong_num})人来贺:<br/>

<#if (page_objs?size > 0)>
	<#list page_objs as obj>
		<@a href="/p?p_VO/${pid}/${obj.name}"/>${obj.id}</a><br/>
	</#list>
	<#if (page>0)><@a href="p?pm_DL/${pid}/${yuelao_npc.id}/${page-1}"/>上页.</a></#if>
	<#if (page<total_page-1)><@a href="p?pm_DL/${pid}/${yuelao_npc.id}/${page+1}"/>下页.</a></#if>
	<#if (total_page>1)>(${page+1}/${total_page})<br/></#if>
</#if>
<@goback />
<@gogame />
</p>
</card>
</wml>