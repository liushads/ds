<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
${yuelao_npc.name}:<br/>
<#if return == 1>
你同意了【${friend.name}】的结婚请求，你可以等待对方或者亲自举办婚礼并发喜包,同时你必须保持在线到婚宴结束才能保证你们能收到礼金,成功举办婚宴后双方同时在线可以获得另一方获得经验的10%，攻击，防御，敏捷基础属性增加5%.
<br/>
<@a href="/p?pm_DL/${pid}/${yuelao_npc.id}"/>亲自举办</a><br/>
</#if>
<#if return == 0>
你拒绝了和【${friend.name}】的结婚请求.<br/>
</#if>
<@goback/>
<@gogame />
</p>
</card>
</wml>