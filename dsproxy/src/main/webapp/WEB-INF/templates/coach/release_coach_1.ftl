<#include "/include/header.ftl">
<card title="${game_title}"><p>
脱离师门需要交付10银，且亲友度清零.<br/>
<#if player.student?exists && player.student.status = 1>
你确定要你师傅断绝师徒关系?<br/>
<@a href="/p?co_R/${pid}/"/>确定</a><br/>
<#elseif player.coach?exists>
师傅不能进行脱离师门操作.<br/>
<#else>
你还没有拜师成功,不能脱离师门哟!<br/>
</#if>
<@goback />
<@gofacility/>
</p></card>
</wml> 