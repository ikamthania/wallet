package com.livelygig.walletclient.views

import com.livelygig.walletclient.handler.AcceptTermsOfServices
import com.livelygig.walletclient.services.WalletCircuit
import diode.AnyAction._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

object TermsOfServiceView {
  def acceptBtnClick() = {
    Callback {
      WalletCircuit.dispatch(AcceptTermsOfServices())
      dom.window.location.href = "#/setup"
    }
  }

  def component = ScalaComponent.static("TermsOfService") {
    <.div(
      <.div(
        ^.className := "wallet-inner-navigation",
        <.div(
          ^.className := "row",
          <.div(
            ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
            <.div(
              ^.className := "wallet-information",
              <.h2("Wallet"),
              <.h3("Terms of Service"))))),
      <.div(
        ^.className := "termsofservice-main scrollableArea",
        <.div(
          ^.className := "termsofservice-section",
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-xs-12",
              <.h1("Privacy Policy"),
              <.h4("1. Introduction"),
              <.p(
                "1.1 LivelyGig recognizes that people value their privacy. This Privacy Policy details important information regarding the collection, use and disclosure of User information collected on the LivelyGig website located at ",
                <.a(^.target := "_blank", ^.href := "https://LivelyGig.com/", "https://LivelyGig.com/"),
                ",",
                <.a(^.target := "_blank", ^.href := "https://ubunda.com/", " https://ubunda.com/ "),
                """(the "Site"), and any other features, tools, materials, or other services (including co-branded or affiliated services) offered from time to time by LivelyGig (the "Service"). LivelyGig provides this Privacy Policy to help you understand how your personal information is used by us and your choices regarding our use of it."""),
              <.p("1.2 This Privacy Policy should be read in conjunction with our Terms of Use. By accessing the Service, you are consenting to the information collection and use practices described in this Privacy Policy."),
              <.p("1.3 Your use of the Service and any personal information you provide through the Service remains subject to the terms of this Privacy Policy and our Terms of Use, as each may be updated from time to time."),
              <.p("1.4 Any questions, comments or complaints that you might have should be emailed to info@LivelyGig.com."),
              <.h4("2. Information We Collect"),
              <.p("By using our Service and by submitting personal information to us through your use of our Service then this Privacy Policy will apply. The personal information we collect from you generally may include:"),
              <.p("2.1 Network information regarding transactions"),
              <.p("2.2 Your first wallet address created through the our Service becomes publicly known to us. We receive only the public address and the address is sent to our server. We do not receive information from subsequent wallet creations through the Service."),
              <.p("2.3 We may receive network information from you as a result of your interaction with our Service."),
              <.p("2.4 Our service requires the highest level of browser permissions that could potentially lead to procurement of more personal information. For information on how we use these permissions for a limited purpose, and why this is necessary, can be found in paragraph 3 below."),
              <.p("2.5 Your interactions with the Site may be documented via Google Analytics and that information is processed by Google."),
              <.h4("3. The Way LivelyGig Uses Your Personal Information"),
              <.p("3.1 Infura uses network information for purposes of maintaining blockchain services and therefore the operational infrastructure required to use the service."),
              <.p("3.2 We become aware of the first public key created by virtue of the system design. All subsequent key creations remain anonymous. The public key we do receive is stored using data security and encryption."),
              <.p("3.3 As with nearly all interactions that take place on the World Wide Web, our servers may receive information by virtue of your interaction with them, including but not limited to IP Addresses."),
              <.p("3.4 Our service requires full browser permissions that could potentially be used to access additional personal information. Such browser permissions are used for an extremely limited technical purpose for allowing the Service to properly interact with your browser. No additional information is obtained beyond what is necessary to provide the Service. No information received is shared with any third-party except those affiliated with LivelyGig or as required for provision of the Service."),
              <.p("3.5 LivelyGig uses Google Analytics for purposes of monitoring web traffic. Any identifying information collected via Google Analytics is controlled by Google."),
              <.p("3.6 Public blockchains provide transparency into transactions and LivelyGig is not responsible for preventing or managing information broadcasted on a blockchain."),
              <.h4("4. When LivelyGig Discloses Information"),
              <.p("4.1 We may disclose your personal information to our subsidiaries, affiliated companies, agents, businesses, or service providers who process your personal information on our behalf in providing the Service to you. Our agreements with these service providers limit the kinds of information they can use or process and ensure they use reasonable efforts to keep your personal information secure."),
              <.p("4.2 LivelyGig also reserves the right to disclose personal information that LivelyGig believes, in good faith, is appropriate or necessary to enforce our Terms of Use, take precautions against liability or harm, to investigate and respond to third-party claims or allegations, to respond to a court orders or official requests, to protect the security or integrity of our Service,and to protect the rights, property, or safety of LivelyGig , our users or others."),
              <.p("4.3 In the event that LivelyGig is involved in a merger, acquisition, sale, bankruptcy, insolvency, reorganization, receivership, assignment for the benefit of creditors, or the application of laws or equitable principles affecting creditors' rights generally, or other change of control, there may be a disclosure of your information to another entity related to such event."),
              <.h4("5. Your Choices"),
              <.p("5.1 LivelyGig will process your personal information in accordance with this Privacy Policy, and as part of that you will have limited or no opportunity to otherwise modify how your information is used by LivelyGig."),
              <.h4("6. Cookies"),
              <.p("6.1 LivelyGig does not use cookies at this time but reserves the right to do so in the future. These terms shall be updated accordingly."),
              <.h4("7. Information Security"),
              <.p("7.1 Whilst neither we, nor any other organization, can guarantee the security of information processed online, we do have appropriate security measures in place to protect your personal information. For example, we store the personal information you provide on computer systems with limited access, encryption, or both."),
              <.h4("8. Your California Privacy Rights"),
              <.p("8.1 If you reside in California, you may request certain general information regarding our disclosure of personal information to third parties for their direct marketing purposes. To make such a request, please write to us at the following address:"),
              <.p("LivelyGig Inc., 16771 NE 80th Street, Redmond, WA 98052>"),
              <.h4("9. Changes and updates"),
              <.p("""9.1 This Privacy Policy may be revised periodically and this will be reflected by the "Last update posted" date above. Please revisit this page to stay aware of any changes. Your continued use of the Service constitutes your agreement to this Privacy Policy and any future revisions."""),
              <.p("9.2 Contact Information: info@LivelyGig.com"))),
          <.div(
            ^.className := "row",
            <.div(
              ^.className := "col-xs-12",
              <.h1("Terms of Use"),
              <.p("The software you as an end user are about to use functions as a free, open source, and multi-signature digital wallet. The software does not constitute an account where LivelyGig or other third parties serve as financial intermediaries or custodians of your bitcoin or ether or any other crypto currency."),
              <.p("While the software has undergone beta testing and continues to be improved by feedback from the end users and others, we cannot guarantee that there will be no defects in the software. You acknowledge that your use of this software is at your own discretion and in compliance with all applicable laws. You are responsible for safekeeping your passwords, private key pairs, PINs and any other codes you use to access the software."),
              <.p("IF YOU LOSE ACCESS TO YOUR UBUNDA WALLET OR YOUR ENCRYPTED PRIVATE KEYS AND YOU HAVE NOT SEPARATELY STORED A BACKUP OF YOUR WALLET AND CORRESPONDING PASSWORD, YOU ACKNOWLEDGE AND AGREE THAT ANY BITCOIN OR ETHER OR ANY CRYTO CURRENCY YOU HAVE ASSOCIATED WITH THAT UBUNDA WALLET WILL BECOME INACCESSIBLE. All transaction requests are irreversible. The authors of the software, employees and affiliates of LivelyGig, copyright holders, and LivelyGig, Inc. cannot retrieve your private keys or passwords if you lose or forget them and cannot guarantee transaction confirmation as they do not have control over the Bitcoin network."),
              <.p("""To the fullest extent permitted by law, this software is provided "as is" and no representations or warranties can be made of any kind, express or implied, including but not limited to the warranties of merchantability, fitness or a particular purpose and noninfringement. You assume any and all risks associated with the use of the software. In no event shall the authors of the software, employees and affiliates of LivelyGig, copyright holders, or LivelyGig, Inc. be held liable for any claim, damages or other liability, whether in an action of contract, tort, or otherwise, arising from, out of or in connection with the software. We reserve the right to modify this disclaimer from time to time."""))),
          <.div(
            ^.className := "container btnDefault-container",
            <.div(
              ^.className := "row",
              <.div(
                ^.className := "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                <.a(^.className := "btn btnDefault btnDefault goupButton", ^.onClick --> acceptBtnClick(), "Accept"),
                <.a(^.href := "/wallet#", ^.className := "btn btnDefault decline btnDefault goupButton", "Decline")))))))
  }

}
