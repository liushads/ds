<#include "/include/header.ftl">
<card title="${game_title}"><p>
${location}<br/>
<#if return==0>
已经向${friend.name}发出离婚请求，请耐心等待对方的处理。<br/>
<#elseif return==1>
已经与${friend.name}解除婚姻关系，现在又是单身了。
损失${lost_money}银。<br/>
</#if>
<@goback/>
<@gogame />
</p>
</card>
</wml>