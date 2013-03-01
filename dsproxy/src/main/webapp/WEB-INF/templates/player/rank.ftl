<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if "1"==type>
等级排行：<br/>
玩家等级由高到低排序<br/>
<#elseif "2"==type>
财富排行：<br/>
玩家财富由高到低排序<br/>
<#elseif   "3"==type>
玫瑰排行：<br/>
玩家玫瑰数由高到低排序<br/>
<#elseif   "4"==type>
浓情玫瑰榜排行：<br/>
玩家浓情玫瑰榜数由高到低排序<br/>
<#elseif   "5"==type>
巧克力玫瑰榜排行：<br/>
玩家巧克力玫瑰榜数由高到低排序<br/>
<#elseif   "6"==type>
推荐排行：<br/>
推荐排行榜按天统计当天获得返利的累积金额<br/>
</#if>
<#if ranks?exists>
<#list ranks as rank>
	${rank_index+1}、<@a href="/p?p_VO/${pid}/${rank.playerId}/"/>${rank.playerName}<#if "1"==type>(${rank.num})</#if></a><br/>
</#list>
</#if>
<@goback/>
<@gogame/>
</p>
</card>
</wml>