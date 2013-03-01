<#include "/include/header.ftl">
<card title="${game_title}"><p>
疑问解答：<br/>
<#if kfList?exists && kfList?size &gt; 0>
<#list kfList as player>
	<@a href="/p?p_VO/${pid}/${player.id}"/>${player.name}</a> <@a href="/p?m_I/${pid}/${player.id}"/>提问</a><#if !player.gm?exists><@a href="/p?p_KF/${pid}/v/${player.id}"/>投票</a></#if> <br/>
</#list>
<#else>
暂时无人，请稍后再来<br/>
</#if>
<@goback />
<@gogame />
</p></card>
</wml>