<#-- 显示可以合成的物品列表 -->
<#include "/include/header.ftl">
<#include "/include/playeritem.ftl">
<card title="${game_title}"><p>
${location}<br/>
合成大师:你好! 有什么可以为你帮忙的吗?<br/>
<#if (itemForgeList?exists && itemForgeList?size > 0)>
合成逐风寒月链的藏宝图被铁匠铺的精炼大师给偷走了，逐风寒月链的图可以用来合成逐风虎跃腿：010，020，030，040和20个神兵之魂。合成不会失败.<br/>
<#list itemForgeList as itemForge>
<@a href="/p?i_LCM/${pid}/${itemForge.itemId}/298/"/>合成</a> <@showItemNoBuy itemForge.item/><br/>
</#list>
<#else>
目前不提供物品合成，请关注公告.
</#if>
<br/>
<@goback/>
<@gofacility/>
<@gogame/>
</p>
</card>
</wml>