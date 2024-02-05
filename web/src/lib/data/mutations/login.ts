export const loginWithUsernameAndPassword = async ({
  username,
  password,
}: {
  username: string
  password: string
}) => {
  const postData = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
    body: new URLSearchParams({
      client_id: `${process.env.NEXT_PUBLIC_IDENTITY_PROVIDER_CLIENT_ID}`,
      username,
      password,
      grant_type: 'password',
    }),
  }

  try {
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_IDENTITY_PROVIDER_URL}/protocol/openid-connect/token`,
      postData
    )

    if (!response.ok) {
      console.log('!response.ok')

      console.log(
        'Error logging in with username and password',
        response.status,
        response.statusText
      )

      return null
    }

    return await response?.json()
  } catch (error) {
    console.log('catch (error)')

    console.log(error)

    return null
  }
}
