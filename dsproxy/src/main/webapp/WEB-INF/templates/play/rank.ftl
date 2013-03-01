<#include "/include/header.ftl">
<card title="${game_title}"><p>
斗神侠侣榜:
<#if ranks?exists>
<br/>
<#list ranks as rank>
	${rank_index+1}、<@a href="/p?p_VO/${pid}/${rank.playerId}/"/>${rank.playerName}</a>|<@a href="/p?p_VO/${pid}/${rank.mateId}/"/>${rank.mateName}</a> [♡:${rank.intimateScore}]<br/>
</#list>
<#else>暂时没有排名,请稍后再来!<br/></#if>
<@goback/>
<@gogame/>
</p>
</card>
</wml>