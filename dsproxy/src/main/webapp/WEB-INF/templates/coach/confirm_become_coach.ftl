<#include "/include/header.ftl">
<card title="${game_title}"><p>
${last_npc.name}：<br/>
<#if err_msg.code = 0>
恭喜你成为师傅，希望你能找到好徒弟,<br/>
当你收到别人申请成为你徒弟的消息后   <br/>
你可以到在好友栏点击师徒, 在那你可 <br/>
以选择接受拜师或者拒绝。<br/>
<#else>
${err_msg.text}<br/>
</#if>
<@gofacility/>
</p></card>
</wml> 