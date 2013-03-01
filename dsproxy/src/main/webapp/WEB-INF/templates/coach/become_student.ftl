<#include "/include/header.ftl">
<card title="${game_title}"><p>
<#if last_npc?exists>
${last_npc.name}：<br/>
</#if>
<#if err_msg.code = 0 && student?exists>
你已经选择了拜入【${student.coachName}】门下，请等待他的答复，同意之后你才能正式成为他的徒弟；
在此期间你暂时不能再申请成为其他人的徒弟，如果超过1天没有答复，你可以重新申请；<br/>
<@a href="/p?m_I/${pid}/${student.coachId}"/>给${student.coachName}发私聊</a><br/>
<#else>
${err_msg.text}<br/>
</#if>
<@goback />
<@gofacility/>
</p></card>
</wml> 