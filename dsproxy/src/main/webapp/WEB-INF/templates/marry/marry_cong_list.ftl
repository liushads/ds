<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
<#if (marry_ing_list?size>0)>
可参加的在线玩家婚宴:<br/>
<#list marry_ing_list as ls>
<@a href="/p?pm_CG/${pid}/${ls.playerId}/"/>
祝贺</a>${ls.playerName}的婚宴(${ls.congSize}人贺)<br/>
</#list>
<#else>
还没有可以参加的婚宴(参加婚宴可以领取喜包礼品，每个婚宴只能参加一次)。<br/>
</#if>
<@goback />
<@gogame />
</p>
</card>
</wml>