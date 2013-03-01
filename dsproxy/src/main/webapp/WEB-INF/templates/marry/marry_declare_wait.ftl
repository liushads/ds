<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
你已经在${location}向${friend.name}发出结婚请求，等对方同意后就可以举办婚宴了.<br/>
如果对方长时间没有受理你的请求，你可以撤销该请求，并重新选择结婚对象.<br/>
<@a href="/p?pm_RC/${pid}"/>撤销结婚请求</a><br/>
<@goback />
<@gogame />
</p>
</card>
</wml>