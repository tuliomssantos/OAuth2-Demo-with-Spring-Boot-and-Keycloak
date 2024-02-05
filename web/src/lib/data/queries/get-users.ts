'use server'

import { cookies } from 'next/headers'

export const getUsers = async () => {
  const cookieStore = cookies()
  const JSSESSION = cookieStore.get('JSESSIONID')

  try {
    const userInfoResponse = await fetch(
      `${process.env.NEXT_PUBLIC_API_URL}/api/users`,
      {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Cookie: `JSESSIONID=${JSSESSION?.value}`,
        },
        credentials: 'include',
        cache: 'no-store',
      }
    )

    if (!userInfoResponse.ok) {
      return null
    }

    return await userInfoResponse?.json()
  } catch (error) {
    console.log(error)
    return null
  }
}
