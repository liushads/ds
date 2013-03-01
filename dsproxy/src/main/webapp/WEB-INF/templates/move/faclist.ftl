<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if direct?exists>
选择目的地: <@goback/>
<#list direct as t>
	<@a href="/p?mv_W/${pid}/${t[0].id}"/>${t[0].name}</a>
	<#if ((t_index+1)%3=0 || !t_has_next)><br/><#else>.</#if>
</#list>
</#if>
<@goback/>
</p></card>
</wml>