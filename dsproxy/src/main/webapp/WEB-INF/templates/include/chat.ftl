<#macro showPrivateMessage private_msg>
<#if private_msg?exists>
<#t><@a href="/p?p_CM/${pid}"/><#if private_msg.fromPlayerId==0>秘书小Q<#elseif private_msg.fromPlayerId == player.id>${private_msg.fromPlayerName}<#else>${private_msg.fromPlayerName}</#if>:<#if private_msg.content?length <= 10>${private_msg.content?html}<#else>${private_msg.content[0..10]?html}...</#if></a><br/>
</#if>
</#macro>

<#macro showPublicMessage page_objs>
<#if (player.level > 5)>
<#if page_objs?exists && (page_objs?size>0)>
<#list page_objs as message> 
	<#t><#if message.typeId==3>[队伍]<#else>[公共]</#if><#if message.fromPlayerId==0>斗神快报<#elseif message.fromPlayerId == player.id>${message.fromPlayerName}<#else><@a href="/p?p_VO/${pid}/${message.fromPlayerId}/"/>${message.fromPlayerName}</a></#if>:<#if message.content?length <= 10>${message.content?html}<#else>${message.content[0..10]?html}...</#if><br/>
</#list>
</#if>
</#if>
</#macro>