<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
人生苦短，相依相爱的两个人啊，为何会走到这一步，你确定要解除与【${friend.name}】的婚姻关系?<br/>
<@a href="/p?pm_LL/${pid}/${friend.id}/0"/>协议离婚(50银)</a>.
<@a href="/p?pm_LL/${pid}/${friend.id}/1"/>强制离婚</a><br/>
<@goback />
<@gogame />
</p>
</card>
</wml>