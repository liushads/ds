<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
<#if match_intimate?exists>
你可以和以下在线亲友申请结婚:<br/>
<#list match_intimate as ls>
<@a href="/p?pm_DD/${pid}/${ls[0].id}/"/>申请和${ls[0].name}结婚</a>(亲友度${ls[1].name})<br/>
</#list>
<#else>
你还没有可以申请结婚的亲友。条件：<br/>
(1)双方单身且对方在线<br/>
(2)互为好友，也就是亲友<br/>
(3)亲友度大于1000<br/>
(4)男女10级以上<br/>
(5)一定的结婚费用<br/>
</#if>
<#if desc?exists>
${desc}<br/>
</#if>
<@goback />
<@gogame />
</p>
</card>
</wml>