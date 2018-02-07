package forms

import play.api.data.Forms._
import play.api.data._

object UserForms {
  val logInForm = Form(mapping(
    "emailOrEthAddress" -> nonEmptyText,
    "password" -> nonEmptyText,
    "rememberMe" -> boolean
  )(LoginData.apply)(LoginData.unapply))

  val registrationForm = Form(mapping(
    "defaultAlias" -> nonEmptyText.verifying("Malformed Alias", e => !e.contains("/")),
    "emailOrEthAddress" -> nonEmptyText,
    "password" -> nonEmptyText
  )(RegistrationData.apply)(RegistrationData.unapply))

  case class LoginData(emailOrEthAddress: String, password: String, rememberMe: Boolean)

  case class RegistrationData(alias: String, emailOrEthAddress: String, password: String)

  case class WalletRegistrationData(alias: String, emailAddress: Option[String], password: String)

  case class PasswordChange(oldPassword: String, newPassword: String, confirm: String)

  case class WalletLoginData(walletIdentity: String, password: String, rememberMe: Boolean)

  val changePasswordForm = Form(mapping(
    "old" -> nonEmptyText,
    "new" -> nonEmptyText,
    "confirm" -> nonEmptyText
  )(PasswordChange.apply)(PasswordChange.unapply))

  val walletRegistrationForm = Form(mapping(
    "defaultAlias" -> nonEmptyText.verifying("Malformed Alias", e => !e.contains("/")),
    "emailAddress" -> optional(text),
    "password" -> nonEmptyText
  )(WalletRegistrationData.apply)(WalletRegistrationData.unapply))

  val walletLogInForm = Form(mapping(
    "walletIdentity" -> nonEmptyText,
    "password" -> nonEmptyText,
    "rememberMe" -> boolean
  )(WalletLoginData.apply)(WalletLoginData.unapply))

  case class FormData(name: String)

  val form = Form(
    mapping(
      "name" -> text
    )(FormData.apply)(FormData.unapply)
  )
}

