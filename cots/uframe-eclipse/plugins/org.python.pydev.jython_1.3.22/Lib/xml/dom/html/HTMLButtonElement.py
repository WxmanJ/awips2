########################################################################
#
# File Name:            HTMLButtonElement
#
# Documentation:        http://docs.4suite.com/4DOM/HTMLButtonElement.html
#

### This file is automatically generated by GenerateHtml.py.
### DO NOT EDIT!

"""
WWW: http://4suite.com/4DOM         e-mail: support@4suite.com

Copyright (c) 2000 Fourthought Inc, USA.   All Rights Reserved.
See  http://4suite.com/COPYRIGHT  for license and copyright information
"""

import string
from xml.dom import Node
from xml.dom.html.HTMLElement import HTMLElement

class HTMLButtonElement(HTMLElement):

    def __init__(self, ownerDocument, nodeName="BUTTON"):
        HTMLElement.__init__(self, ownerDocument, nodeName)

    ### Attribute Methods ###

    def _get_accessKey(self):
        return self.getAttribute("ACCESSKEY")

    def _set_accessKey(self, value):
        self.setAttribute("ACCESSKEY", value)

    def _get_disabled(self):
        return self.hasAttribute("DISABLED")

    def _set_disabled(self, value):
        if value:
            self.setAttribute("DISABLED", "DISABLED")
        else:
            self.removeAttribute("DISABLED")

    def _get_form(self):
        parent = self.parentNode
        while parent:
            if parent.nodeName == "FORM":
                return parent
            parent = parent.parentNode
        return None

    def _get_name(self):
        return self.getAttribute("NAME")

    def _set_name(self, value):
        self.setAttribute("NAME", value)

    def _get_tabIndex(self):
        value = self.getAttribute("TABINDEX")
        if value:
            return int(value)
        return 0

    def _set_tabIndex(self, value):
        self.setAttribute("TABINDEX", str(value))

    def _get_type(self):
        return self.getAttribute("TYPE")

    def _get_value(self):
        return self.getAttribute("VALUE")

    def _set_value(self, value):
        self.setAttribute("VALUE", value)

    ### Attribute Access Mappings ###

    _readComputedAttrs = HTMLElement._readComputedAttrs.copy()
    _readComputedAttrs.update({
        "accessKey" : _get_accessKey,
        "disabled" : _get_disabled,
        "form" : _get_form,
        "name" : _get_name,
        "tabIndex" : _get_tabIndex,
        "type" : _get_type,
        "value" : _get_value
        })

    _writeComputedAttrs = HTMLElement._writeComputedAttrs.copy()
    _writeComputedAttrs.update({
        "accessKey" : _set_accessKey,
        "disabled" : _set_disabled,
        "name" : _set_name,
        "tabIndex" : _set_tabIndex,
        "value" : _set_value
        })

    _readOnlyAttrs = filter(lambda k,m=_writeComputedAttrs: not m.has_key(k),
                     HTMLElement._readOnlyAttrs + _readComputedAttrs.keys())
